const container = document.getElementById('container');
const registerBtn = document.getElementById('register');
const loginBtn = document.getElementById('login');

registerBtn.addEventListener('click', () => {
    container.classList.add("active");
});

loginBtn.addEventListener('click', () => {
    container.classList.remove("active");
});

$(document).ready(function () {
    // On change of the country dropdown
    $('#countryId').change(function () {
        var selectedCountryId = $(this).val();

        // Fetch cities for the selected country using AJAX
        $.ajax({
            url: '/api/cities/' + selectedCountryId,
            type: 'GET',
            success: function (data) {
                // Clear existing options and add new options for cities
                $('#cityId').empty().append('<option value="">Select a city</option>');
                $.each(data, function (index, city) {
                    $('#cityId').append('<option value="' + city.id + '">' + city.name + '</option>');
                });
            },
            error: function () {
                console.error('Error fetching cities');
            }
        });
    });
});

const usernameInput = document.getElementById('username');
const passwordInput = document.getElementById('password');

fetch('/users/login', {
    method: 'POST',
    headers: {
        'Content-Type': 'application/json'
    },
    body: JSON.stringify({
        username: usernameInput.value,
        password: passwordInput.value
    })
})
    .then(response => {
        if (response.ok) {
            // Redirect to the home page
            window.location.href = '/';
        } else {
            // Handle login error
            console.error('Login failed');
        }
    })