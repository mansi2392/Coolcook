(function() {
    'use strict';

    angular
        .module('coolcookApp')
        .controller('CategoryMySuffixController', CategoryMySuffixController);

    CategoryMySuffixController.$inject = ['Category'];

    function CategoryMySuffixController(Category) {

        var vm = this;

        vm.categories = [];

        loadAll();

        function loadAll() {
            Category.query(function(result) {
                vm.categories = result;
                vm.searchQuery = null;
            });
        }
    }
})();
