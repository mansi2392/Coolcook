(function() {
    'use strict';

    angular
        .module('coolcookApp')
        .controller('QuantityMySuffixController', QuantityMySuffixController);

    QuantityMySuffixController.$inject = ['Quantity'];

    function QuantityMySuffixController(Quantity) {

        var vm = this;

        vm.quantities = [];

        loadAll();

        function loadAll() {
            Quantity.query(function(result) {
                vm.quantities = result;
                vm.searchQuery = null;
            });
        }
    }
})();
