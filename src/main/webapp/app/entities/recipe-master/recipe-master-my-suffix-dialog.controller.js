(function() {
    'use strict';

    angular
        .module('coolcookApp')
        .controller('RecipeMasterMySuffixDialogController', RecipeMasterMySuffixDialogController);

    RecipeMasterMySuffixDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'RecipeMaster', 'Category', 'IngredientQtyMapping'];

    function RecipeMasterMySuffixDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, RecipeMaster, Category, IngredientQtyMapping) {
        var vm = this;

        vm.recipeMaster = entity;
        vm.clear = clear;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;
        vm.categories = Category.query();
        vm.ingredientqtymappings = IngredientQtyMapping.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.recipeMaster.id !== null) {
                RecipeMaster.update(vm.recipeMaster, onSaveSuccess, onSaveError);
            } else {
                RecipeMaster.save(vm.recipeMaster, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('coolcookApp:recipeMasterUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


        vm.setImage = function ($file, recipeMaster) {
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        recipeMaster.image = base64Data;
                        recipeMaster.imageContentType = $file.type;
                    });
                });
            }
        };

    }
})();
