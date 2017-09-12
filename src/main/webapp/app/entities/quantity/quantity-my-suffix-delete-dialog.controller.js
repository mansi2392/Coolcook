(function() {
    'use strict';

    angular
        .module('coolcookApp')
        .controller('QuantityMySuffixDeleteController',QuantityMySuffixDeleteController);

    QuantityMySuffixDeleteController.$inject = ['$uibModalInstance', 'entity', 'Quantity'];

    function QuantityMySuffixDeleteController($uibModalInstance, entity, Quantity) {
        var vm = this;

        vm.quantity = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Quantity.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
