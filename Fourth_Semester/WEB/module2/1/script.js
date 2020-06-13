function isValidDate(dateString)
{
    // First check for the pattern
    if(!/^\d{1,2}\.\d{1,2}\.\d{4}$/.test(dateString))
        return false;

    // Parse the date parts to integers
    var parts = dateString.split(".");
    var day = parseInt(parts[0], 10);
    var month = parseInt(parts[1], 10);
    var year = parseInt(parts[2], 10);

    // Check the ranges of month and year
    if(year < 1000 || year > 3000 || month == 0 || month > 12)
        return false;

    var monthLength = [ 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 ];

    // Adjust for leap years
    if(year % 400 == 0 || (year % 100 != 0 && year % 4 == 0))
        monthLength[1] = 29;

    // Check the range of the day
    return day > 0 && day <= monthLength[month - 1];
};

function validate() {
	let error = document.getElementById("error");
	let success = document.getElementById("success");
	error.innerHTML = "";
	success.innerHTML = "";

	let name = document.forms["Form"]["Name"].value;
	let age = document.forms["Form"]["Age"].value;
	let salary = document.forms["Form"]["Salary"].value;
	let birthday = document.forms["Form"]["Birthday"].value;

	let password = document.forms["Form"]["Password"].value;
	let confirmPassword = document.forms["Form"]["ConfirmPassword"].value;

	if(name == "") {
		error.innerHTML = "Please, enter your name";
		return false;
	}
	if(age != "" && !Number.isInteger(+age)) {
		error.innerHTML ="Age has to be integer";
		return false;
	}
	if(salary!="" && !/^\d+(\.\d{1,2})?$/.test(salary)) {
		error.innerHTML = "Salary has to decimal with max of 2 numbers after .";
		return false;
	}
	if(birthday!="" && !isValidDate(birthday)) {
		error.innerHTML = "Data has to be in format DD.MM.YYYY";
		return false;
	}
	if(password == "") {
		error.innerHTML ="Password has to be specified";
		return false;
	}
	if(confirmPassword != password) {
		error.innerHTML ="Confirm password must be equal to password";
		return false;
	}
	success.innerHTML ="Form is valid";
	return false;
}