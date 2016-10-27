(function() {
    'use strict';
    angular
        .module('rhinobuyApp')
        .factory('Casa', Casa);

    Casa.$inject = ['$resource'];

    function Casa ($resource) {
        var resourceUrl =  'api/casas/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
