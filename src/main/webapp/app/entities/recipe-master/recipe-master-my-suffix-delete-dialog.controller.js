(function() {
    'use strict';

    angular
        .module('coolcookApp')
        .controller('RecipeMasterMySuffixDeleteController',RecipeMasterMySuffixDeleteController);

    RecipeMasterMySuffixDeleteController.$inject = ['$uibModalInstance', 'entity', 'RecipeMaster'];

    function RecipeMasterMySuffixDeleteController($uibModalInstance, entity, RecipeMaster) {
        var vm = this;

        vm.recipeMaster = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            RecipeMaster.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
