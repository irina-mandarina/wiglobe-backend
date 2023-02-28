package com.example.demo.models.responseModels

data class SearchResult(val users: List<UserNames>,
                        val journeys: List<Journey>,
                        val destinations: List <DestinationSearchResult>
)