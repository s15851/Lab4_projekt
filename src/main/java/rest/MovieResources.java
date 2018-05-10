package rest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import domain.Actor;
import domain.Comment;
import domain.Movie;
import domain.Rating;
import domain.services.MovieService;

@Path("/movies")
public class MovieResources {
	
	private MovieService db = new MovieService();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Movie> getAll() {
		return db.getAll();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response Add(Movie movie) {
		db.add(movie);
		return Response.ok(movie.getId()).build();
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response get(@PathParam("id") int id) {
		Movie result = db.getById(id);
		if (result == null) {
			return Response.ok("Provided Movie ID does not exist").build();
		}
		return Response.ok(result).build();
	}
	
	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response update(@PathParam("id") int id, Movie movie) {
		Movie result = db.getById(id);
		if (result == null) {
			return Response.ok("Provided Movie ID does not exist").build();
		}
		movie.setId(id);
		db.update(movie);
		return Response.ok().build();
	}
	
	@DELETE
	@Path("/{id}")
	public Response delete(@PathParam("id") int id) {
		Movie result = db.getById(id);
		if (result == null) {
			return Response.ok("Provided Movie ID does not exist").build();
		}
		db.delete(result);
		return Response.ok().build();
	}
	
	@GET
	@Path("/{movieId}/comments")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Comment> getComments(@PathParam("movieId") int movieId) {
		Movie result = db.getById(movieId);
		if (result == null) {
			return null;
		}
		if (result.getComments() == null) {
			result.setComments(new ArrayList<Comment>());
		}
		return result.getComments();
	}
	
	@POST
	@Path("/{id}/comments")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addComment(@PathParam("id") int movieId, Comment comment) {
		Movie result = db.getById(movieId);
		if (result == null) {
			return Response.ok("Provided Movie ID does not exist").build();
		}
		if (result.getComments() == null) {
			result.setComments(new ArrayList<Comment>());
		}
		
		db.addComment(result, comment);
		return Response.ok().build();
	}
	
	@GET
	@Path("/{movieId}/ratings")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Rating> getRatings(@PathParam("movieId") int movieId) {
		Movie result = db.getById(movieId);
		if (result == null) {
			return null;
		}
		if (result.getRatings() == null) {
			result.setRatings(new ArrayList<Rating>());
		}
		return result.getRatings();
	}
	
	@POST
	@Path("/{id}/ratings")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addRatings(@PathParam("id") int movieId, Rating rating) {
		Movie result = db.getById(movieId);
		if (result == null) {
			return Response.ok("Provided Movie ID does not exist").build();
		}
		if (result.getRatings() == null) {
			result.setRatings(new ArrayList<Rating>());
		}
		db.addRating(result, rating);
		return Response.ok().build();
	}
	
	//Display actors for given movie
	
	@GET
	@Path("/{movieId}/actors")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Actor> getActors(@PathParam("movieId") int movieId) {
		Movie result = db.getById(movieId);
		if (result == null) {
			return null;
		}
		return db.addActor(movieId);
	}
}
