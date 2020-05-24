package server.network;

import org.junit.jupiter.api.Test;
import shared.networking.RMIServer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class RMIServerImplTest {
    RMIServer server;
    //Log in-zero
    @Test
    void logInZero() throws RemoteException, SQLException, FileNotFoundException {
        server.logIn(null, null);
       assertEquals(null,server.getProfile(null));
    }

    //log in-one
    @Test
    void logInOne() throws RemoteException, SQLException, FileNotFoundException {
        server.logIn("Roksana", "kotkot");
        assertEquals("Roksana",server.getProfile("Roksana"));

    }

    //log in-many
    @Test
    void logInMany() throws RemoteException, SQLException, FileNotFoundException {
        server.logIn("Roksana", "kotkot");
        server.logIn("Julia","Kot");
        assertEquals("Julia",server.getProfile("Julia"));
        assertEquals("Roksana",server.getProfile("Roksana"));
    }
    //log in-boundary
    @Test
    void logInBoundary()
    {
        //it is not necessary in this method
    }

    //log in- exceptions
    @Test
    void logInExceptions()
    {
        assertThrows(IllegalArgumentException.class,()->server.logIn("toms","kotyyy"));
    }
    //add recipe-zero
    @Test
    void addRecipeZero() throws IOException, SQLException {
        server.addRecipe(null,null,null,null,null,null);
        assertEquals(null,server.getRecipesByTitle(null));
        assertEquals(null, server.getRecipesByIngredient(null));
    }
    //add recipe-one
    @Test
    void addRecipeOne() throws IOException, SQLException {
        ArrayList<String> ingredients = new ArrayList<>();
        server.addRecipe("kotlety","usmaz kotlety","kotki",ingredients,null,null);
        assertEquals("kotlety",server.getRecipesByTitle("kotlety"));
        assertEquals(ingredients.get(0),server.getRecipesByIngredient(ingredients.get(0)));
    }
    //add recipe-many
    @Test
    void addRecipeMany() throws IOException, SQLException {
        ArrayList<String> ingredients1 = new ArrayList<>();
        ArrayList<String> ingredients2 = new ArrayList<>();
        server.addRecipe("kotlety","usmaz kotlety","kotki",ingredients1,null,null);
        server.addRecipe("pierogi","ugotuj pierogi","pieski",ingredients2,null,null);

        assertEquals("kotlety",server.getRecipesByTitle("kotlety"));
        assertEquals(ingredients1.get(0),server.getRecipesByIngredient(ingredients1.get(0)));

        assertEquals("pierogi",server.getRecipesByTitle("pierogi"));
        assertEquals(ingredients2.get(0),server.getRecipesByIngredient(ingredients2.get(0)));
    }

    //add recipe-boundary
    @Test
    void addRecipeBoundary()
    {
        //it's not necessary here, i think
    }
    //add recipe-exception
    @Test
    void addRecipeException()
    {
        assertThrows(IllegalArgumentException.class, ()->server.addRecipe("Fries","frytki","Julia",null,null,null))
    }
    @Test
    void report() {
    }

    @Test
    void signUp() {
    }

    @Test
    void editProfile() {
    }

    @Test
    void getProfile() {
    }

    @Test
    void getProfiles() {
    }

    @Test
    void subscribe() {
    }

    @Test
    void unsubscribe() {
    }

    @Test
    void doIsubscribeIt() {
    }

    @Test
    void delete() {
    }

    @Test
    void getRecipesByIngredient() {
    }

    @Test
    void getRecipeByTitle() {
    }

    @Test
    void getRecipesByUsername() {
    }

    @Test
    void getRecipesByTitle() {
    }

    @Test
    void getAllRecipes() {
    }
}