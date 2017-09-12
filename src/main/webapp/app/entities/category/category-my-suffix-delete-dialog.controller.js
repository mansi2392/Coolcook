(function() {
    'use strict';

    angular
        .module('coolcookApp')
        .controller('CategoryMySuffixDeleteController',CategoryMySuffixDeleteController);

    CategoryMySuffixDeleteController.$inject = ['$uibModalInstance', 'entity', 'Category'];

    function CategoryMySuffixDeleteController($uibModalInstance, entity, Category) {
        var vm = this;

        vm.category = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Category.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
