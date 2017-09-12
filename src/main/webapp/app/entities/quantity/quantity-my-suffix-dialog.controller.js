(function() {
    'use strict';

    angular
        .module('coolcookApp')
        .controller('QuantityMySuffixDialogController', QuantityMySuffixDialogController);

    QuantityMySuffixDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Quantity'];

    function QuantityMySuffixDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Quantity) {
        var vm = this;

        vm.quantity = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.quantity.id !== null) {
                Quantity.update(vm.quantity, onSaveSuccess, onSaveError);
            } else {
                Quantity.save(vm.quantity, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('coolcookApp:quantityUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
