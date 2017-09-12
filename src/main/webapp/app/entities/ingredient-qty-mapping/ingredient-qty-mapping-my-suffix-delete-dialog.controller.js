(function() {
    'use strict';

    angular
        .module('coolcookApp')
        .controller('IngredientQtyMappingMySuffixDeleteController',IngredientQtyMappingMySuffixDeleteController);

    IngredientQtyMappingMySuffixDeleteController.$inject = ['$uibModalInstance', 'entity', 'IngredientQtyMapping'];

    function IngredientQtyMappingMySuffixDeleteController($uibModalInstance, entity, IngredientQtyMapping) {
        var vm = this;

        vm.ingredientQtyMapping = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            IngredientQtyMapping.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
