module.exports = () => {
    const date = new Date();
    let year = date.getFullYear();
    let month = ("0" + (date.getMonth() + 1)).slice(-2);
    let day = ("0" + date.getDate()).slice(-2);
    let hour = ("0" + date.getHours()).slice(-2);
    let minute = ("0" + date.getMinutes()).slice(-2);
    let seconds = ("0" + date.getSeconds()).slice(-2);

    // prints date & time in YYYY-MM-DD HH:MM:SS format
    return year + "-" + month + "-" + day + " " + hour + ":" + minute + ":"
        + seconds + "Z";
};

