(function() {
    'use strict';

    angular
        .module('coolcookApp')
        .controller('IngredientMasterMySuffixDialogController', IngredientMasterMySuffixDialogController);

    IngredientMasterMySuffixDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'IngredientMaster', 'Category'];

    function IngredientMasterMySuffixDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, IngredientMaster, Category) {
        var vm = this;

        vm.ingredientMaster = entity;
        vm.clear = clear;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;
        vm.categories = Category.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.ingredientMaster.id !== null) {
                IngredientMaster.update(vm.ingredientMaster, onSaveSuccess, onSaveError);
            } else {
                IngredientMaster.save(vm.ingredientMaster, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('coolcookApp:ingredientMasterUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


        vm.setImage = function ($file, ingredientMaster) {
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        ingredientMaster.image = base64Data;
                        ingredientMaster.imageContentType = $file.type;
                    });
                });
            }
        };

    }
})();
