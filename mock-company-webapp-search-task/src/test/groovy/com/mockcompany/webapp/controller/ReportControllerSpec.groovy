package com.mockcompany.webapp.controller

import com.mockcompany.webapp.api.SearchReportResponse
import com.mockcompany.webapp.model.ProductItem
import com.mockcompany.webapp.services.SearchService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import spock.lang.Specification


import javax.transaction.Transactional

@SpringBootTest
class ReportControllerSpec extends Specification {

    @MockBean
    SearchService searchService

    @Autowired
    ReportController reportController

    ProductItem createItem(String name, String description = name) {
        return new ProductItem(name: name, description: description, cost: 100, image: "")
    }
    
    @Transactional
    def "reports correct number of results for each important term"() {
        given:
        mockDataService.setupMockData(
        Collection<ProductItem> coolItems = [
                createItem("The Cool Product", "This product is cool"),
                createItem("The Other Product 1", "This product is cool too"),
                createItem("The Other Cool Product", "This product is 2 kool 4 skool"),
        ]
        Collection<ProductItem> amazingItems = [
                createItem("The Amazing Product", "This product is amazing"),
                createItem("The Other Product 2", "This product is amazing too")
        ]
        Collection<ProductItem> perfectItems = [
                createItem("Just Perfect", "This product is just perfect!"),
        ]
        Collection<ProductItem> kidItems = [
                createItem("Ninja Turtles Stuff", "Do kids even like these anymore?"),
                createItem("Kids Phones", "Because kidz need to make calls too, you know!")
        ]
        Collection<ProductItem> otherItems = [
                createItem("Boring Item 1", "Why do we sell this?"),
                createItem("Another Boring Item", "This product should probably be marketed at Kidz")
        ])
        

        when:
        def report = reportController.runReport()

        then:
        response.productCount == 10
        response.searchTermHits?.get("Cool") == 3
        response.searchTermHits?.get("Amazing") == 2
        response.searchTermHits?.get("Perfect") == 1
        response.searchTermHits?.get("Kids") == 2
    }

}
