const dateString = require("./date");
module.exports = async ({ github, pr, repo }) => {
    let body = pr.body;
    let remaining = (body.match(/\[ *\]/g) || []).length;
    let total = (body.match(/\[.?]/g) || []).length;
    let checked = total - remaining;

    const dateStr = dateString();

    let status = {
        context: "Lint PR / check checkbox checks",
        sha: pr.head.sha,
        state: "pending",
        description: checked + " / " + total + " tasks completed | " + dateStr,
    };

    // if all finished, say finished
    if (remaining === 0) {
        status.state = "success";
        status.description = "All " + checked + " / " + total
            + " tasks have been completed | " + dateStr;
    }

    // send check back to GitHub
    let payload = Object.assign({}, repo, status);
    let response = await github.rest.repos.createCommitStatus(payload);
};

