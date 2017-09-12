(function() {
    'use strict';

    angular
        .module('coolcookApp')
        .controller('IngredientQtyMappingMySuffixController', IngredientQtyMappingMySuffixController);

    IngredientQtyMappingMySuffixController.$inject = ['IngredientQtyMapping'];

    function IngredientQtyMappingMySuffixController(IngredientQtyMapping) {

        var vm = this;

        vm.ingredientQtyMappings = [];

        loadAll();

        function loadAll() {
            IngredientQtyMapping.query(function(result) {
                vm.ingredientQtyMappings = result;
                vm.searchQuery = null;
            });
        }
    }
})();
