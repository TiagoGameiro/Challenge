package com.angels.challenge.service

import com.angels.challenge.model.Product
import com.angels.challenge.model.StoreProduct
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.*
import org.springframework.retry.annotation.Retryable
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.lang.Exception
import java.util.Collections.synchronizedList


@Service
class FetchDataService {
    final inline fun <reified T: Any> typeReference(): ParameterizedTypeReference<T> = object : ParameterizedTypeReference<T>() {}

    private val headers = object : HttpHeaders() {
        init {
            set("API-Key", "76a325g7g2ahs7h4673aa25s47632h5362a4532642")
        }
    }

    private val client: WebClient = WebClient.builder()
        .baseUrl("http://134.209.29.209:3000")
        .codecs { config ->
            config.defaultCodecs()
                .maxInMemorySize(16 * 1024 * 1024)
        }
        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .build()

    final inline fun <reified T> getAllPaginatedData(startingPage: Int, path: String): MutableList<T>? {
        var page = startingPage
        return getData<List<T>> (
            pathToAppend = "$path?page=$page",
            httpMethod = HttpMethod.GET,
            responseType = typeReference()
        ).expand { response ->
            if (response.isNullOrEmpty()) {
                return@expand Mono.empty<List<T>?>()
            }
            page += 1
            getData (
                pathToAppend = "$path?page=$page",
                httpMethod = HttpMethod.GET,
                responseType = typeReference()
            )
        }.flatMap { response -> Flux.fromIterable(response as Iterable<T>) }
            .collectList().block()
    }

    fun <T> getData (
        pathToAppend: String,
        httpMethod: HttpMethod,
        requestBody: Any? = "",
        responseType: ParameterizedTypeReference<T>
    ): Mono<T> {
        return client.method(httpMethod)
            .uri(pathToAppend)
            .header("API-Key", "76a325g7g2ahs7h4673aa25s47632h5362a4532642")
            .header("Accept", "text/csv")
            .body(BodyInserters.fromValue(requestBody as Any))
            .retrieve()
            .bodyToMono(responseType)
            .retry(5)
    }

    fun getStoreProductPerPage(page: Int, path: String): MutableList<StoreProduct> {
        for (i in 1..5) {
            try {
                println(page)
                val response: ResponseEntity<MutableList<StoreProduct>> = RestTemplate().exchange(
                    "http://134.209.29.209:3000/$path?page=$page",
                    HttpMethod.GET,
                    HttpEntity("parameters", headers),
                    typeReference<MutableList<StoreProduct>>()
                )
                return response.body!!
            } catch (e: Exception) {
                continue
            }
        }
        return emptyList<StoreProduct>().toMutableList()
    }
}