(function() {
    'use strict';

    angular
        .module('coolcookApp')
        .controller('QuantityMySuffixController', QuantityMySuffixController);

    QuantityMySuffixController.$inject = ['Quantity', 'QuantitySearch'];

    function QuantityMySuffixController(Quantity, QuantitySearch) {

        var vm = this;

        vm.quantities = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            Quantity.query(function(result) {
                vm.quantities = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            QuantitySearch.query({query: vm.searchQuery}, function(result) {
                vm.quantities = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
