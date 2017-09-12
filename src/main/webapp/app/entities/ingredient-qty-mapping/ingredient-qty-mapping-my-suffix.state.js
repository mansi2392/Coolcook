(function() {
    'use strict';

    angular
        .module('coolcookApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('ingredient-qty-mapping-my-suffix', {
            parent: 'entity',
            url: '/ingredient-qty-mapping-my-suffix',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'coolcookApp.ingredientQtyMapping.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/ingredient-qty-mapping/ingredient-qty-mappingsmySuffix.html',
                    controller: 'IngredientQtyMappingMySuffixController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('ingredientQtyMapping');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('ingredient-qty-mapping-my-suffix-detail', {
            parent: 'ingredient-qty-mapping-my-suffix',
            url: '/ingredient-qty-mapping-my-suffix/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'coolcookApp.ingredientQtyMapping.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/ingredient-qty-mapping/ingredient-qty-mapping-my-suffix-detail.html',
                    controller: 'IngredientQtyMappingMySuffixDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('ingredientQtyMapping');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'IngredientQtyMapping', function($stateParams, IngredientQtyMapping) {
                    return IngredientQtyMapping.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'ingredient-qty-mapping-my-suffix',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('ingredient-qty-mapping-my-suffix-detail.edit', {
            parent: 'ingredient-qty-mapping-my-suffix-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/ingredient-qty-mapping/ingredient-qty-mapping-my-suffix-dialog.html',
                    controller: 'IngredientQtyMappingMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['IngredientQtyMapping', function(IngredientQtyMapping) {
                            return IngredientQtyMapping.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('ingredient-qty-mapping-my-suffix.new', {
            parent: 'ingredient-qty-mapping-my-suffix',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/ingredient-qty-mapping/ingredient-qty-mapping-my-suffix-dialog.html',
                    controller: 'IngredientQtyMappingMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('ingredient-qty-mapping-my-suffix', null, { reload: 'ingredient-qty-mapping-my-suffix' });
                }, function() {
                    $state.go('ingredient-qty-mapping-my-suffix');
                });
            }]
        })
        .state('ingredient-qty-mapping-my-suffix.edit', {
            parent: 'ingredient-qty-mapping-my-suffix',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/ingredient-qty-mapping/ingredient-qty-mapping-my-suffix-dialog.html',
                    controller: 'IngredientQtyMappingMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['IngredientQtyMapping', function(IngredientQtyMapping) {
                            return IngredientQtyMapping.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('ingredient-qty-mapping-my-suffix', null, { reload: 'ingredient-qty-mapping-my-suffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('ingredient-qty-mapping-my-suffix.delete', {
            parent: 'ingredient-qty-mapping-my-suffix',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/ingredient-qty-mapping/ingredient-qty-mapping-my-suffix-delete-dialog.html',
                    controller: 'IngredientQtyMappingMySuffixDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['IngredientQtyMapping', function(IngredientQtyMapping) {
                            return IngredientQtyMapping.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('ingredient-qty-mapping-my-suffix', null, { reload: 'ingredient-qty-mapping-my-suffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
