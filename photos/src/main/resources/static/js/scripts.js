function APIRequest(url, func, request = "GET", data = null) {
    let el;

    if (request === 'POST' || request === "PUT") {
        $.ajax({

            url: url,
            dataType: "json", // Для использования JSON формата получаемых данных
            method: request, // Что бы воспользоваться POST методом, меняем данную строку на POST
            data: data,
            success: function (data) {
                console.log(data); // Возвращаемые данные выводим в консоль
                func(data);
            }
        });
        return
    } else {
        el = {'method': request};
    }

    fetch(url, el)
        .then(
            function (response) {
                if (response.status !== 200) {
                    console.log('Looks like there was a problem. Status Code: ' +
                        response.status);
                    return;
                }

                // Examine the text in the response
                response.json().then(function (data) {
                    func(data)
                });
            }
        )
        .catch(function (err) {
            console.log('Fetch Error :-S', err);
        });
}

$(".username-search").on("change paste keyup", function () {
    console.log($(this).val());
    if ($(this).val()) {
        if ($('.search-results').css('display') == "none") {
            $('.search-results').css('display', 'block');
        }
        APIRequest('/api/users?username=' + $(this).val(), function (users) {
            $('.search-results').html('');

            console.log(users);
            for (let user of users) {
                $('.search-results').append(`
                    <div class="flex-block">
                        <a href="/p/${user.username}" style="display: flex;">
                            <div class="flex-left">
                                <img src="/uploads/${user.avatar}" style="border-radius:50%; 
                                width: 50px;
                                height: 50px;" alt="">
                            </div>
                            <div class="flex-right ml-2">
                                <h6>${user.username}</h6>
                                <h7 class="color-grey">${user.first_name} ${user.last_name}</h7>
                            </div>
                        </a>
                    </div>
                    <hr>
                `);
            }
        });
    } else {
        $('.search-results').css('display', 'none');
    }
});

function getRandomInt(min, max) {
    min = Math.ceil(min);
    max = Math.floor(max);
    return Math.floor(Math.random() * (max - min + 1)) + min;
}

$('.follow').click(function () {
    console.log({'subId': parseInt($("#user_id").text()), 'userId': parseInt($("#profile_id").text())});
    APIRequest('/api/subscribers', function (res) {
        $('.follow').text('Followed');
        $('#followers-counter').text(parseInt($('#followers-counter').text()) + 1);
    }, "POST", {'subId': parseInt($("#user_id").text()), 'userId': parseInt($("#profile_id").text())})
});

$(".post").click(function () {
    console.log("username: " + $(this).data("name"));
    $("#modalLongTitle").text($(this).data("name"));

    console.log("Images: " + $(this).children("img"));
    console.log($(`#photos-${$(this).data("id")}`).find('img'));
    console.log($(`#photos-${$(this).data("id")}`));
    console.log(`#photos-${$(this).data("id")}`);
    console.log($(this));

    let images = $(`#photos-${$(this).data("id")}`).find('img').map(function () {
        return $(this).attr('src')
    }).get();

    for (let el of images) {
        $("#modal-body").append(`<img src="${el}" style="width: 100%;">`);
    }
    $("#modal-body").append(`<p>${$(this).data("text")}</p>`);
});

$("#modalLong").on("hidden.bs.modal", function () {
    $("#modal-body").html('');
});

function findGetParameter(parameterName) {
    let result = null,
        tmp = [];
    location.search
        .substr(1)
        .split("&")
        .forEach(function (item) {
            tmp = item.split("=");
            if (tmp[0] === parameterName) result = decodeURIComponent(tmp[1]);
        });
    return result;
}

function sendDelete(url) {
    $.ajax({
        url: url,
        method: 'delete',
    })
}