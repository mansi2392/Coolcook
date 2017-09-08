(function() {
    'use strict';

    angular
        .module('coolcookApp')
        .controller('IngredientMasterDialogController', IngredientMasterDialogController);

    IngredientMasterDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'IngredientMaster'];

    function IngredientMasterDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, IngredientMaster) {
        var vm = this;

        vm.ingredientMaster = entity;
        vm.clear = clear;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;

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
            if ($file && $file.$error === 'pattern') {
                return;
            }
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
