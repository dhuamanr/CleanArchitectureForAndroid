package com.mitteloupe.whoami.history.data.repository

import com.mitteloupe.whoami.datasource.history.datasource.IpAddressHistoryDataSource
import com.mitteloupe.whoami.datasource.history.model.SavedIpAddressHistoryRecordDataModel
import com.mitteloupe.whoami.history.data.mapper.SavedIpAddressRecordToDomainMapper
import com.mitteloupe.whoami.history.domain.model.SavedIpAddressRecordDomainModel
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.collection.IsCollectionWithSize.hasSize
import org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.given

@RunWith(MockitoJUnitRunner::class)
class ConnectionHistoryRepositoryTest {
    private lateinit var classUnderTest: ConnectionHistoryRepository

    @Mock
    private lateinit var ipAddressHistoryDataSource: IpAddressHistoryDataSource

    @Mock
    private lateinit var connectionDetailsToDataMapper: SavedIpAddressRecordToDomainMapper

    @Before
    fun setUp() {
        classUnderTest =
            ConnectionHistoryRepository(ipAddressHistoryDataSource, connectionDetailsToDataMapper)
    }

    @Test
    fun `Given history records when history then returns all items`() {
        // Given
        val dataHistoryRecord1 = dataHistoryRecord("1.1.1.1")
        val domainHistoryRecord1 = domainHistoryRecord("M.A.P.1")
        given(connectionDetailsToDataMapper.toDomain(dataHistoryRecord1))
            .willReturn(domainHistoryRecord1)
        val dataHistoryRecord2 = dataHistoryRecord("2.2.2.2")
        val domainHistoryRecord2 = domainHistoryRecord("M.A.P.2")
        given(connectionDetailsToDataMapper.toDomain(dataHistoryRecord2))
            .willReturn(domainHistoryRecord2)
        val givenHistory = setOf(dataHistoryRecord1, dataHistoryRecord2)
        given(ipAddressHistoryDataSource.allRecords()).willReturn(givenHistory)

        // When
        val actualHistory = classUnderTest.history()

        // Then
        assertThat(actualHistory, hasSize(2))
        assertThat(actualHistory, containsInAnyOrder(domainHistoryRecord1, domainHistoryRecord2))
    }

    private fun dataHistoryRecord(ipAddress: String) = SavedIpAddressHistoryRecordDataModel(
        ipAddress,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        123L
    )

    private fun domainHistoryRecord(ipAddress: String) = SavedIpAddressRecordDomainModel(
        ipAddress,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        321L
    )
}
