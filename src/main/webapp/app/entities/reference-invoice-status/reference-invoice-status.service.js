(function() {
    'use strict';
    angular
        .module('rhinobuyApp')
        .factory('ReferenceInvoiceStatus', ReferenceInvoiceStatus);

    ReferenceInvoiceStatus.$inject = ['$resource', 'DateUtils'];

    function ReferenceInvoiceStatus ($resource, DateUtils) {
        var resourceUrl =  'api/reference-invoice-statuses/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.invoiceDate = DateUtils.convertLocalDateFromServer(data.invoiceDate);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.invoiceDate = DateUtils.convertLocalDateToServer(copy.invoiceDate);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.invoiceDate = DateUtils.convertLocalDateToServer(copy.invoiceDate);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();
