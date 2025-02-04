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
function general(){
	window.location.href = 'SimpleLink';
}
function output(){
	document.cookie = 'uuid=; Max-age=-1';
	document.cookie = 'name=; Max-age=-1';
	window.location.href = 'SimpleLink';
}
function openLinkMenu(i){
	var menu = document.getElementsByClassName("LinkMenu");
	let item = document.getElementsByClassName(i + ".1");
	let longLink = item[0].innerHTML;
	item = document.getElementsByClassName(i + ".2");
	let shortLink = item[0].innerHTML;
	shortLink = shortLink.replace("http://localhost:8080/SimpleLinkapp/lnk/", "");
	item = document.getElementsByClassName(i + ".3");
	let transition = item[0].innerHTML;
	item = document.getElementsByClassName(i + ".4");
	let availTransition = item[0].innerHTML;
	item = document.getElementsByClassName(i + ".5");
	let ttl = item[0].innerHTML;
	let menuLong = document.getElementsByClassName("LongLink");
	let menuShort = document.getElementsByClassName("ShortLink");
	let menuTrans = document.getElementsByClassName("Transition");
	let menuAvailTrans = document.getElementsByClassName("AvailTransition");
	let menuTtl = document.getElementsByClassName("TimeToLife");
		if (menu[0].style.display != "inline-flex"){
			menu[0].style.display = "inline-flex";
			menuLong[0].value = longLink;
			menuShort[0].value = shortLink;
			menuTrans[0].value = transition;
			menuAvailTrans[0].value = availTransition;
			menuTtl[0].value = ttl;
		}
}
function closeLinkMenu(){
	var menu = document.getElementsByClassName("LinkMenu");
			if (menu[0].style.display != "none"){
				menu[0].style.display = "none";
			}
}
function getCookie(name){
	const fullCookieString = '; ' + document.cookie;
	const splitCookie = fullCookieString.split('; ' + name + '=');
	return splitCookie.length === 2 ? splitCookie.pop().split(';').shift() : null;
}
function deleteLink(i){
	let xhr = new XMLHttpRequest();
	let data = document.getElementsByClassName(i + ".2");
	let uuid = getCookie("uuid");
	let shortLink = data[0].innerHTML;
	shortLink = shortLink.replace("http://localhost:8080/SimpleLinkapp/lnk/", "");
	let body = 'uuid=' + encodeURIComponent(uuid) + '&shortLink=' + encodeURIComponent(shortLink);
	xhr.overrideMimeType("application/x-www-form-urlencoded");
	xhr.open("POST", "http://localhost:8080/SimpleLinkapp/DeleteLink", true);
	xhr.onreadystatechange = function () {};
	xhr.send(body);
	location.reload();
}





