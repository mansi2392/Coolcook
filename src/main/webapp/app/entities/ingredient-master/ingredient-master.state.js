(function() {
    'use strict';

    angular
        .module('coolcookApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('ingredient-master', {
            parent: 'entity',
            url: '/ingredient-master?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'coolcookApp.ingredientMaster.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/ingredient-master/ingredient-masters.html',
                    controller: 'IngredientMasterController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('ingredientMaster');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('ingredient-master-detail', {
            parent: 'ingredient-master',
            url: '/ingredient-master/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'coolcookApp.ingredientMaster.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/ingredient-master/ingredient-master-detail.html',
                    controller: 'IngredientMasterDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('ingredientMaster');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'IngredientMaster', function($stateParams, IngredientMaster) {
                    return IngredientMaster.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'ingredient-master',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('ingredient-master-detail.edit', {
            parent: 'ingredient-master-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/ingredient-master/ingredient-master-dialog.html',
                    controller: 'IngredientMasterDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['IngredientMaster', function(IngredientMaster) {
                            return IngredientMaster.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('ingredient-master.new', {
            parent: 'ingredient-master',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/ingredient-master/ingredient-master-dialog.html',
                    controller: 'IngredientMasterDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                ingredientId: null,
                                name: null,
                                image: null,
                                imageContentType: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('ingredient-master', null, { reload: 'ingredient-master' });
                }, function() {
                    $state.go('ingredient-master');
                });
            }]
        })
        .state('ingredient-master.edit', {
            parent: 'ingredient-master',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/ingredient-master/ingredient-master-dialog.html',
                    controller: 'IngredientMasterDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['IngredientMaster', function(IngredientMaster) {
                            return IngredientMaster.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('ingredient-master', null, { reload: 'ingredient-master' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('ingredient-master.delete', {
            parent: 'ingredient-master',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/ingredient-master/ingredient-master-delete-dialog.html',
                    controller: 'IngredientMasterDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['IngredientMaster', function(IngredientMaster) {
                            return IngredientMaster.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('ingredient-master', null, { reload: 'ingredient-master' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
