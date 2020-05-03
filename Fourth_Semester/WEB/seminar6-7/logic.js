let books=[
		createBook("Петров. Кібернетика", 10.5),
		createBook("Algorithms", 20),
		createBook("Math", 100),
		createBook("Music", 10),
		createBook("Sport", 15.5)
	];

function createBook(name, price) {
	return {
		name,
		price
	};
}

function getSelectedBook() {
	let bookIndex = document.getElementById("books").value;
	return books[bookIndex];
}

function calculatePrice() {
	let amount = document.getElementById("amount").value;

	let price = getSelectedBook().price * amount;
	document.getElementById("price").value = price;

	return price;
}

function getCheckedRadio() {
	let radios = document.getElementsByName("delivery");
	for(let radio of radios) {
		if(radio.checked) {
			return radio;
		}
	}
}

function calculateDelivery() {
	let price = calculatePrice();

	let radio = getCheckedRadio();

	if(radio.value=="postDelivery") {
		price *= 1.05;
	} else if (radio.value == "homeDelivery") {
		price *= 1.15;
	}

	let isHolidayDelivery = document.getElementsByName("holiday-delivery")[0].checked;

	if(isHolidayDelivery) {
		price *= 1.1;
	}

	let totalPriceInput = document.getElementById("total-price");
	totalPriceInput.value = price;

	return price;
}

function reset() {
	document.getElementById("form").reset();
}

function getDeliveryStr() {
	let deliveryStr;

	let checkedRadio = getCheckedRadio();
	if(checkedRadio.value=="postDelivery") {
		deliveryStr = "Поштою"
	} else if (checkedRadio.value == "homeDelivery") {
		deliveryStr = "До дому"
	}

	let isHolidayDelivery = document.getElementsByName("holiday-delivery")[0].checked;

	if(isHolidayDelivery) {
		deliveryStr = deliveryStr + " + " + "У святковій упаковці";
	}

	return deliveryStr;
}

function submitForm() {
	let price = calculateDelivery();
	let bookName = getSelectedBook().name;
	let amount = document.getElementById("amount").value;
	let deliveryStr = getDeliveryStr();
	let name = document.getElementById("name").value;
	let address = document.getElementById("address").value;

	
	let order = {
		bookName: bookName,
		amount: amount,
		delivery: deliveryStr,
		price: price,
		name: name,
		address: address 
	};

	if(!validateOrder(order)) {
		return false;
	}

	submitOrder(order);

	reset();

	return false;
}

function validateOrder(order) {
	if(order.name=="") {
		alert("Опишіть імя замовника");
		return false;
	}
	if(order.address=="") {
		alert("Опишіть адрес замовника");
		return false;
	}
	if(order.amount=="" || order.amount==0) {
		alert("Опишіть кількість книг");
		return false;
	}
	return true;
}

function submitOrder(order) {
	let table = document.getElementById("orders");
	let row = document.createElement("tr");

	let bookNameCol = document.createElement("td");
	bookNameCol.textContent = order.bookName;
	let amountCol = document.createElement("td");
	amountCol.textContent = order.amount;
	let deliveryCol = document.createElement("td");
	deliveryCol.textContent = order.delivery;
	let priceCol = document.createElement("td");
	priceCol.textContent = order.price;
	let nameCol = document.createElement("td");
	nameCol.textContent = order.name;
	let addressCol = document.createElement("td");
	addressCol.textContent = order.address;

	row.appendChild(nameCol);
	row.appendChild(addressCol);
	row.appendChild(bookNameCol);
	row.appendChild(amountCol);
	row.appendChild(deliveryCol);
	row.appendChild(priceCol);

	table.appendChild(row);
}

function init() {
	let booksSelect = document.getElementById("books");
	let amountInput = document.getElementById("amount");

	for(const book of books) {
		let option = document.createElement("option");
		option.value = books.indexOf(book);
		option.text = book.name + "-" + book.price;
		booksSelect.appendChild(option);
	}


}
window.onload = init;