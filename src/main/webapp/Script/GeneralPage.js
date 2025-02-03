function ddmenu() {
	var menu = document.getElementsByClassName("user-menu");
	if (menu[0].style.display != "flex"){
		menu[0].style.display = "flex";
	} else {
		menu[0].style.display = "none";
	}
}
function login() {
	window.location.href = 'Login';
}
function registration() {
	window.location.href = 'Registration';
}
function lk(){
	window.location.href = 'LC';
}
function output(){
	document.cookie = 'uuid=; Max-age=-1';
	document.cookie = 'name=; Max-age=-1';
	window.location.href = 'SimpleLink';
}
function loginByUuid(){
	var login = document.getElementsByClassName("loginByUuid");
	if (login[0].style.display != "flex"){
		login[0].style.display = "flex";
	} else {
		login[0].style.display = "none";
	}
}