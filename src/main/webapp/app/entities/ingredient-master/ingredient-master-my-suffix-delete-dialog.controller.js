(function() {
    'use strict';

    angular
        .module('coolcookApp')
        .controller('IngredientMasterMySuffixDeleteController',IngredientMasterMySuffixDeleteController);

    IngredientMasterMySuffixDeleteController.$inject = ['$uibModalInstance', 'entity', 'IngredientMaster'];

    function IngredientMasterMySuffixDeleteController($uibModalInstance, entity, IngredientMaster) {
        var vm = this;

        vm.ingredientMaster = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            IngredientMaster.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
