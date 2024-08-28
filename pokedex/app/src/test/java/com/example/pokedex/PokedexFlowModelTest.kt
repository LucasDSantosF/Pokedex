package com.example.pokedex

import app.cash.turbine.test
import com.example.pokedex.model.service.PokedexService
import com.example.pokedex.ui.step.PokedexFlowModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.rules.TestWatcher
import org.junit.runner.Description

@OptIn(ExperimentalCoroutinesApi::class)
class PokedexFlowModelTest {
    private lateinit var flowModel: PokedexFlowModel
    private lateinit var service: FakePokedexService

    private val dispatcher = UnconfinedTestDispatcher()

    @get:Rule
    val coroutineTestRule = CoroutineTestRule(dispatcher)

    @Before
    fun setup() {
        service = FakePokedexService()

        flowModel = PokedexFlowModel(
            service = service
        )
    }

    @Test
    fun `should return correct pokemon list`() = runTest(dispatcher) {
        flowModel.getList()

        flowModel.state.test {
            advanceUntilIdle()

            val state = expectMostRecentItem()

            assertEquals(PokedexTestData.pokemonList().results, state.list)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `should return correct pokemon type list`() = runTest(dispatcher) {
        flowModel.getList()

        flowModel.state.test {
            advanceUntilIdle()

            val state = expectMostRecentItem()

            assertEquals(PokedexTestData.pokemonDetail().types, state.typeList)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `should return correct pokemon list by type`() = runTest(dispatcher) {
        flowModel.getPokemonByType("some-id")

        flowModel.state.test {
            advanceUntilIdle()

            val state = expectMostRecentItem()

            assertEquals(PokedexTestData.pokemonList().results, state.list)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `should return correct pokemon detail`() = runTest(dispatcher) {
        flowModel.getDetail("some-id")

        flowModel.state.test {
            advanceUntilIdle()

            val state = expectMostRecentItem()

            assertEquals(PokedexTestData.pokemonDetail(), state.details)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `should reload list if coming to detail to list and pokemon type selected`() =
        runTest(dispatcher) {
            flowModel.updateSelectedType("3")

            flowModel.state.test {
                advanceUntilIdle()

                val state = expectMostRecentItem()

                assertEquals("3", state.selectedType)
                cancelAndIgnoreRemainingEvents()
            }
        }
}

@ExperimentalCoroutinesApi
class CoroutineTestRule(
    private val dispatcher: CoroutineDispatcher = UnconfinedTestDispatcher()
) : TestWatcher() {

    override fun starting(description: Description) {
        Dispatchers.setMain(dispatcher)
    }

    override fun finished(description: Description) {
        Dispatchers.resetMain()
    }
}