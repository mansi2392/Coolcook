(function() {
    'use strict';

    angular
        .module('coolcookApp')
        .controller('IngredientAtHomeMySuffixDialogController', IngredientAtHomeMySuffixDialogController);

    IngredientAtHomeMySuffixDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 
        'IngredientAtHome', 'IngredientQtyMapping', 'IngredientMaster'];

    function IngredientAtHomeMySuffixDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, IngredientAtHome, 
    IngredientQtyMapping, IngredientMaster) {
        var vm = this;

        vm.ingredientAtHome = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.ingredientqtymappings = IngredientQtyMapping.query();
        vm.ingredients = IngredientMaster.query();
        
        vm.ingredientAtHome.createdAt = new Date();
        
        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.ingredientAtHome.id !== null) {
                IngredientAtHome.update(vm.ingredientAtHome, onSaveSuccess, onSaveError);
            } else {
                IngredientAtHome.save(vm.ingredientAtHome, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('coolcookApp:ingredientAtHomeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.createdAt = false;
        vm.datePickerOpenStatus.updatedAt = false;
        vm.datePickerOpenStatus.expiryDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
