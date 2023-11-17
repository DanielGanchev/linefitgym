

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

