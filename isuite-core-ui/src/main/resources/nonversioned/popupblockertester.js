var popUpTimer;
var popUpTestWindow;

var failureMessage = "The e-ISuite system has detected that Pop-up Blockers are enabled (turned on) for this website. Pop-up Blockers must be turned off in order to View/Print e-ISuite Reports.\n"
	+ "\n"
	+ "Steps to turn off the Pop-up Blocker for e-ISuite in Internet Explorer 11:\n"
	+ "\n"
	+ "\t1.	Select the Tools menu option\n"
	+ "\t2.	Select the Pop-up Blocker option\n"
	+ "\t3.	Select Pop-up Blocker settings\n"
	+ "\t4.	Enter the Web address for the e-ISuite System into\n" 
	+ "\t   the Address of website to allow field\n"
	+ "\t5.	Click the Add button\n"
	+ "\t6.	Click the Close button to close the window\n"
	+ "\n"
	+ "Note:  Other Internet Browsers such as Chrome and Firefox require different steps to turn off Pop-up Blockers. Access the Browser’s help system for information on managing Pop-up Blockers.\n";

function popupBlockerTester(){
	popUpTimer = setTimeout(function(){popUpFailed()}, 2500);
	popUpTestWindow = window.open('popuptesterwindow.html', 
			'popUpTest', 
		'width=1,height=1,left=0,top=0,location=no,menubar=no,resizable=no,scrollbars=no,status=no,titlebar=no,toolbar=no');
}
	
function callBackFromPopUpTesterWindow(){
	clearTimeout(popUpTimer);
	popUpTestWindow.close();
	//alert('DEBUG MESSAGE ONLY: Popups allowed');
}

function popUpFailed(){
	alert(failureMessage);
}
