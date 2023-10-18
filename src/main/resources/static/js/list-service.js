$(document).ready(function () {
    $('#imageModal').on('show.bs.modal', function (event) {
        var button = $(event.relatedTarget); // Button that triggered the modal
        var serviceIndex = button.data('service-index'); // Get the service index from data attribute

        // Set the active slide in the imageCarousel based on the service index
        $('#imageCarousel .carousel-inner .carousel-item').removeClass('active');
        $('#imageCarousel .carousel-inner .carousel-item:nth-child(' + (serviceIndex + 1) + ')').addClass('active');
    });
});