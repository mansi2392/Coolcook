(function() {
    'use strict';

    angular
        .module('coolcookApp')
        .controller('IngredientQtyMappingMySuffixController', IngredientQtyMappingMySuffixController);

    IngredientQtyMappingMySuffixController.$inject = ['IngredientQtyMapping', 'IngredientQtyMappingSearch'];

    function IngredientQtyMappingMySuffixController(IngredientQtyMapping, IngredientQtyMappingSearch) {

        var vm = this;

        vm.ingredientQtyMappings = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            IngredientQtyMapping.query(function(result) {
                vm.ingredientQtyMappings = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            IngredientQtyMappingSearch.query({query: vm.searchQuery}, function(result) {
                vm.ingredientQtyMappings = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
