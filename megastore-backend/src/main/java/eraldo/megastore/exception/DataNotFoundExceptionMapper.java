package eraldo.megastore.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import eraldo.megastore.model.ErrorMessage;

@Provider // registers it in JAX-RS so it can map the exception
public class DataNotFoundExceptionMapper implements ExceptionMapper<DataNotFoundException> {

	@Override
	public Response toResponse(DataNotFoundException ex) {
		// code to create and return custom response

		ErrorMessage errorMessage = new ErrorMessage(ex.getMessage(), 404, "http//google.com");

		return Response.status(Status.NOT_FOUND).entity(errorMessage).build();

	}

}
