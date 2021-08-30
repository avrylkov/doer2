function charsQuote() {
    $('#quote-datalist').empty();

    let chars = $("#quoteSearch").val();
    //console.log(chars);
    $.ajax({
        url: "/dataSearchQuote",
        type: "GET",
        data: {"chars": chars}
    }).then(function (doersAndQuote) {
        //console.log(doerList);
        doersAndQuote.forEach(function (item) {
            // Create a new <option> element.
            //$('#quote-datalist').append($("<option>").text(item.text));
            $('#quote-datalist').append($("<option>").text(item));
        });
    });
}


function charsByPerson() {
    $('#doer-datalist').empty();
    let chars = $("#doerSearch").val();
    //console.log(chars);
    $.ajax({
        url: "/dataSearchDoerByName",
        type: "GET",
        data: {"chars" : chars}
    }).then(function(doersSurname) {
        //console.log(doerList);
        doersSurname.forEach(function(item) {
            // Create a new <option> element.
            $('#doer-datalist').append($("<option>").text(item));
        });
    });
}

function charsSurname() {
    $('#doerInsert-datalist').empty();

    let chars = $("#doerInsertSearch").val();
    //console.log(chars);
    $.ajax({
        url: "/dataSearchByInsertDoer",
        type: "GET",
        data: {"chars": chars}

    }).then(function (doers) {
        //console.log(doerList);
        doers.forEach(function (item) {
            // Create a new <option> element.
            $('#doerInsert-datalist').append($("<option>").text(item));
        });
    });
}

function charsQuoteBySurname() {
    $('#quote-datalist').empty();

    let charsQuote = $("#quoteSearch").val();
    let charsSurName = $("#doerInsertSearch").val();
    //console.log(chars);
    $.ajax({
        url: "/quoteInsert",
        type: "GET",
        data: {"charsQuote": charsQuote, "chars": charsSurName}
    }).then(function (doersAndQuote) {

        //console.log(doerList);
        doersAndQuote.forEach(function (item) {
            // Create a new <option> element.
            $('#quote-datalist').append($("<option>").text(item));
        });
    });
}

