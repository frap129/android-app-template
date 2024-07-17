#!/usr/bin/env nodejs
const dateString = require("./date");

// validate the following things
// * PR title(git commit subject) follows conventional commits
// * PR title(git commit subject) is less than 50 char
//   * unless "allow-long-title" tag
// * PR body, above `****` is less than 72 chars per line
// * PR body must have ticket trailer

const commitSubjectLength = 50;
const commitSubjectLengthLong = 72;
const longCommitSubjectTag = "allow-long-subject";

const commitBodyLength = 72;

const validTypes = [
    "build",
    "ci",
    "docs",
    "feat",
    "fix",
    "perf",
    "refactor",
    "style",
    "test",
    "chore",
    "revert",
];

// custom error class so we can pass non-fatal messages back
class ValidatorError extends Error {
    constructor(...params) {
        // Pass remaining arguments (including vendor specific ones) to parent constructor
        super(...params);

        // Maintains proper stack trace for where our error was thrown (only available on V8)
        if (Error.captureStackTrace) {
            Error.captureStackTrace(this, ValidatorError);
        }
        this.name = "ValidatorError";
    }
}

// based on https://github.com/tunnckoCore/opensource/blob/master/%40packages/parse-commit-message/src/utils.js#L21=
function parseCommitSubject(subject) {
    const regex = /^(\w+)(?:\((.+)\))?(!)?: (.+[^.,!?:;])$/;

    if (!regex.test(subject)) {
        throw new ValidatorError(
            "PR title cannot be matched to the conventional commit format",
        );
    }

    const matches = regex.exec(subject).slice(1);

    if (!matches) {
        throw new ValidatorError(
            "PR title cannot be matched to the conventional commit format",
        );
    }

    const [type, scope = null, bang = "", description] = matches;

    return {
        type,
        breaking: bang != "",
        scope,
        description,
    };
}

let validateCommitBody = (body) => {
    let bodyArr = body.split(/\r?\n/);
    let i = 0;
    let ticketTrailer = false;

    while (i < bodyArr.length) {
        if (bodyArr[i] == "****") {
            if (!ticketTrailer) {
                throw new ValidatorError("Commit Body has no ticket trailer");
            }
            return;
        }
        if (bodyArr[i].length > commitBodyLength) {
            throw new ValidatorError(
                "Commit Body line " + i + " longer than " + commitBodyLength,
            );
            return;
        }
        if (bodyArr[i].match(/^ticket: /)) {
            ticketTrailer = true;
        }
        ++i;
    }
    throw new ValidatorError("Didn't find commit separator **** in PR body");
};

let validateCommitSubject = (subject, allowLongSubject = false) => {
    const { type, breaking, scope, description } = parseCommitSubject(subject);
    if (!validTypes.includes(type)) {
        throw new ValidatorError("PR title invalid conventional commit type");
    }
    let allowedLength = allowLongSubject
        ? commitSubjectLengthLong
        : commitSubjectLength;
    if (subject.length > allowedLength) {
        throw new ValidatorError(
            "PR title too long: " + subject.length + "/" + allowedLength
                + (allowLongSubject
                    ? ""
                    : "; if you must, add the label '" + longCommitSubjectTag
                        + "' to allow up to " + commitSubjectLengthLong),
        );
    }
};

let github_runner = async ({ github, repo, pr, core }) => {
    let title = pr.title;
    let body = pr.body;
    let sha = pr.head.sha;
    let labels = pr.labels.map((l) => l.name);

    let state = "pending";
    let valid = false;

    let errMsg = "";

    const dateStr = dateString();

    try {
        validateCommitSubject(title, labels.includes(longCommitSubjectTag));
        if (!title.startsWith("chore(release)")) {
            validateCommitBody(body);
        }
        valid = true;
        state = "success";
    } catch (e) {
        if (e instanceof ValidatorError) {
            errMsg = e.message + " | " + dateStr;
            state = "pending";
            valid = false;
        } else {
            console.error(e);
            core.setFailed(e.message);
            errMsg = "Unhandled error: " + e.message + " | " + dateStr;
            state = "failed";
        }
    }

    let status = {
        context: "Lint PR / confirm conventional commits compatible",
        sha: sha,
        state: state,
        description: valid ? "PR looks good " + dateStr : errMsg,
    };
    // send check back to GitHub
    let payload = Object.assign({}, repo, status);
    console.log(payload);
    let response = await github.rest.repos.createCommitStatus(payload);
    return true;
};

module.exports = {
    github_runner,
    validateCommitSubject,
    validateCommitBody,
};

// ***************************** testing stuff ********************************
function validateBodyBool(body) {
    return testingErrorCatcher(body, validateCommitBody);
}
function validateCommitSubjectBool(subject) {
    return testingErrorCatcher(subject, validateCommitSubject);
}

function testingErrorCatcher(subject, func) {
    try {
        func(subject);
        return true;
    } catch (e) {
        if (e instanceof ValidatorError) {
            return false;
        } else {
            throw e;
        }
    }
}

// if we're running this file, do testing
if (require.main === module) {
    let valid = [
        "feat: do stuff",
        "ci(some scope)!: things",
    ];
    let invalid = [
        "feat!!: things",
        "badType: things",
        "feat2: still a bad type",
        "things",
        "docs: just kept talking in one long incredibly unbroken sentence moving from topic to topic so that no one had a chance to interrupt, it was really quite hypnotic",
        "DOCS: stuff",
        "ci: Stuff.",
        "ci: Stuff!",
        "ci: Stuff?",
        "ci: Stuff;",
        "ci: Stuff:",
    ];

    let fail = false;

    for (const s of valid) {
        if (validateCommitSubjectBool(s) != true) {
            console.log("failed valid subject: '" + s + "'");
            fail = true;
        }
    }
    for (const s of invalid) {
        if (validateCommitSubjectBool(s) == true) {
            console.log("passed invalid subject: '" + s + "'");
            fail = true;
        }
    }

    let validBodies = [
        `some stuff

ticket: BV-none
****
some other stuff`,
    ];
    let invalidBodies = [
        `just kept talking in one long incredibly unbroken sentence moving from topic to topic so that no one had a chance to interrupt, it was really quite hypnotic
ticket: BV-none
****
some other stuff`,
        `some stuff
no ticket
****
some other stuff`,
    ];

    for (const b of validBodies) {
        if (validateBodyBool(b) != true) {
            console.log("failed valid body: '" + b + "'");
            fail = true;
        }
    }
    for (const b of invalidBodies) {
        if (validateBodyBool(b) == true) {
            console.log("passed invalid body: '" + b + "'");
            fail = true;
        }
    }

    console.log((fail ? "Didn't pass" : "Passed") + " tests");
}

