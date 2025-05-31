package com.ronreynolds.smartsheet.it;

import com.ronreynolds.smartsheet.ApiException;
import com.ronreynolds.smartsheet.api.FavoritesApi;
import com.ronreynolds.smartsheet.api.util.Constants;
import com.ronreynolds.smartsheet.model.AddFavorite200Response;
import com.ronreynolds.smartsheet.model.Favorite;
import com.ronreynolds.smartsheet.model.FavoriteInclude;
import com.ronreynolds.smartsheet.model.FavoriteType;
import com.ronreynolds.smartsheet.model.GenericResult;
import com.ronreynolds.smartsheet.model.GetFavorites200Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * API tests for FavoritesApi
 */
@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FavoritesApiTest {

    private final FavoritesApi api = new FavoritesApi();


    /**
     * Add Favorites
     * <p>
     * Adds one or more favorite items for the current user. This operation supports both single-object and bulk semantics. For
     * more information, see Optional Bulk Operations. If called with a single Favorite object, and that favorite already
     * exists, error code 1129 is returned. If called with an array of Favorite objects, any objects specified in the array
     * that are already marked as favorites are ignored and omitted from the response.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    @Order(1)
    public void addFavoriteTest() throws ApiException {
        List<Favorite> newFavorites = List.of(
                Favorite.builder().objectId(TestData.DashboardData.id).type(FavoriteType.SIGHT).build(),
                Favorite.builder().objectId(TestData.FolderData.id).type(FavoriteType.FOLDER).build()
        );
        String xSmarScActorId = null;
        AddFavorite200Response response = api.addFavorite(newFavorites, xSmarScActorId, Constants.noContentType);
//        log.info("{}", response);
        assertThat(response).satisfies(TestData::successfulResult);
        assertThat(response.getResult()).satisfies(result -> {
            assertThat(result)
                    .anySatisfy(favorite -> {
                        assertThat(favorite.getObjectId()).isEqualTo(TestData.DashboardData.id);
                        assertThat(favorite.getType()).isSameAs(FavoriteType.SIGHT);
                    })
                    .anySatisfy(favorite -> {
                        assertThat(favorite.getObjectId()).isEqualTo(TestData.FolderData.id);
                        assertThat(favorite.getType()).isSameAs(FavoriteType.FOLDER);
                    });
        });
    }

    /**
     * Delete Multiple Favorites
     * <p>
     * Deletes all favorites with the same type for the user.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    @Order(10)
    public void deleteFavoritesByTypeTest() throws ApiException {
        FavoriteType favoriteType = FavoriteType.SIGHT;
        List<Long> objectIds = List.of(TestData.DashboardData.id);
        String xSmarScActorId = null;
        GenericResult response = api.deleteFavoritesByType(favoriteType, objectIds, xSmarScActorId);
        assertThat(response).satisfies(TestData::successfulResult);
    }

    /**
     * Delete Favorite
     * <p>
     * Deletes a single favorite from the user&#39;s list of favorite items by type and ID.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    @Order(10)
    public void deleteFavoritesByTypeAndIdTest() throws ApiException {
        FavoriteType favoriteType = FavoriteType.FOLDER;
        Long favoriteId = TestData.FolderData.id;
        String xSmarScActorId = null;
        GenericResult response = api.deleteFavoritesByTypeAndId(favoriteType, favoriteId, xSmarScActorId);
        assertThat(response).satisfies(TestData::successfulResult);
    }

    /**
     * Get Favorites
     * <p>
     * Gets a list of all of the user&#39;s favorite items.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getFavoritesTest() throws ApiException {
        String xSmarScActorId = null;
        Boolean includeAll = true;
        Integer page = null;
        Integer pageSize = null;
        List<FavoriteInclude> include = Arrays.asList(FavoriteInclude.values());
        GetFavorites200Response response = api.getFavorites(xSmarScActorId, includeAll, page, pageSize, include);
        assertThat(response).satisfies(TestData::pagedResultHasData);
        assertThat(response.getData()).satisfies(TestData.FavoriteData::assertContains);
    }

    /**
     * Is Favorite
     * <p>
     * Checks whether an item has been tagged as a favorite for the current user by type and ID.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void isFavoriteTest() throws ApiException {
        String xSmarScActorId = null;   // FIXME not sure how to test this yet
        List<FavoriteInclude> include = Arrays.asList(FavoriteInclude.values());
        for (Favorite favorite : TestData.FavoriteData.favorites) {
            FavoriteType favoriteType = favorite.getType();
            Long favoriteId = favorite.getObjectId();
            Favorite response = api.isFavorite(favoriteType, favoriteId, xSmarScActorId, include);
            assertThat(response).isNotNull().isEqualTo(favorite);
        }
    }
}