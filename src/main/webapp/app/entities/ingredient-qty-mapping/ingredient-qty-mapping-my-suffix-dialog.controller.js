(function() {
    'use strict';

    angular
        .module('coolcookApp')
        .controller('IngredientQtyMappingMySuffixDialogController', IngredientQtyMappingMySuffixDialogController);

    IngredientQtyMappingMySuffixDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'IngredientQtyMapping', 'Quantity', 'IngredientMaster'];

    function IngredientQtyMappingMySuffixDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, IngredientQtyMapping, Quantity, IngredientMaster) {
        var vm = this;

        vm.ingredientQtyMapping = entity;
        vm.clear = clear;
        vm.save = save;
        vm.quantities = Quantity.query();
        vm.ingredientmasters = IngredientMaster.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.ingredientQtyMapping.id !== null) {
                IngredientQtyMapping.update(vm.ingredientQtyMapping, onSaveSuccess, onSaveError);
            } else {
                IngredientQtyMapping.save(vm.ingredientQtyMapping, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('coolcookApp:ingredientQtyMappingUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
