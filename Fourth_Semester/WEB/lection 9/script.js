//first
function firstTask(n) {
	for(let i=0;i<n;i++) {
		if(i%3==0 && i%5==0) {
			console.log('fizzbuzz');
			continue;
		}
		if(i%3==0) {
			console.log('fizz');
			continue;
		}
		if(i%5==0) {
			console.log('buzz');
			continue;
		}
	}
}

function secondTask(hrn, cop) {
	hrn+= Math.trunc(cop/100);
	cop%=100;
	let grivni;
	if(hrn%10==1) {
		grivni = " гривня ";
	}
	else if (hrn%10>=2 && hrn%10<=4) {
		grivni = " гривні ";
	}
	else {
		grivni = " гривень ";
	}

	let copiyki;
	if(cop%10==1) {
		copiyki = " копійка ";
	}
	else if (cop%10>=2 && cop%10<=4) {
		copiyki = " копійки ";
	}
	else {
		copiyki = " копійок ";
	}

	console.log('Вартість замовлення '+hrn+grivni+cop+copiyki);
}

function thirdTask(x1,y1,x2,y2) {
	let first = getChvert(x1,y1);
	let second = getChvert(x2,y2);

	if(first==second) {
		console.log('Both are in '+first+' chvert. Distance='+dist(x1,y1,x2,y2));
		return;
	}

	printChvert(x1,y1);
	printChvert(x2,y2);
}

function getChvert(x,y) {
	if(x>=0 && y>=0) {
		return 1;
	}
	if(x<=0 && y>=0) {
		return 2;
	}
	if(x<=0 && y<=0) {
		return 3;
	}
	if(x>=0 && y<=0) {
		return 4;
	}
}

function printChvert(x,y) {
	if(x>=0 && y>=0) {
		console.log('Point ('+x+","+y+") is in 1 chvert");
	}
	if(x<=0 && y>=0) {
		console.log('Point ('+x+","+y+") is in 2 chvert");
	}
	if(x<=0 && y<=0) {
		console.log('Point ('+x+","+y+") is in 3 chvert");
	}
	if(x>=0 && y<=0) {
		console.log('Point ('+x+","+y+") is in 4 chvert");
	}
}

function dist(x1,y1,x2,y2) {
	return Math.sqrt((x1-x2)*(x1-x2)+(y1-y2)*(y1-y2));
}
