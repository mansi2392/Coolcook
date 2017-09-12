(function() {
    'use strict';

    angular
        .module('coolcookApp')
        .controller('IngredientAtHomeMySuffixDeleteController',IngredientAtHomeMySuffixDeleteController);

    IngredientAtHomeMySuffixDeleteController.$inject = ['$uibModalInstance', 'entity', 'IngredientAtHome'];

    function IngredientAtHomeMySuffixDeleteController($uibModalInstance, entity, IngredientAtHome) {
        var vm = this;

        vm.ingredientAtHome = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            IngredientAtHome.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
