window.onload=checkMaxWidth;

function checkMaxWidth() {
	var prodpic = document.getElementById("browseproductform:productImage");
	var image_1 = new Image();
	image_1.src = prodpic["src"];
	if (image_1["width"] <= 470) {
		prodpic["width"]=image_1["width"];
		prodpic["height"]=image_1["height"];
		var zoomlink = document.getElementById("browseproductform:zoomlink2");
		zoomlink.style.display="none";
	}
}

function display_fullscreen(myimage) {
	var html = "<html>"
	+"    <head>"
	+"  <script>"
	+"    function filler() {"
	+"        browseHeight=document.body.clientHeight;"
	+"    }"
	+"    <" + "/script>"
	+"    </head>"
	+"    <body bgcolor='#ffffff' onload='filler()'>"
	+"    <div style='float:right'>"
	+"    <a href='#' onclick='window.close();'><img src='../images/close_red.png' alt='close' title='Close' border='0'></a>"
	+"    </div>"
	+"    <table width='100%' height='80%'><tr>"
	+"    <td align='center'>"
	+"    <a href='#' onclick='window.close();'><img src='"+myimage+"' style='border:4px inset #990000;'></a>"
	+"    <br /><i>click picture to close window<i>"
	+"    </table>"
	+"    </body>"
	+"    </html>";
	var popup = window.open("","image","resizable=0");
	popup.moveTo(0,0);
	popup.resizeTo(screen.width, screen.height);
	popup.document.write(html);
	popup.document.focus();
};
