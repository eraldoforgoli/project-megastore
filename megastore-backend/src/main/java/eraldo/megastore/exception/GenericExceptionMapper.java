package eraldo.megastore.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import eraldo.megastore.model.ErrorMessage;

@Provider
public class GenericExceptionMapper implements ExceptionMapper<Throwable> {

	@Override
	public Response toResponse(Throwable ex) {
		// code to create and return custom response

		ErrorMessage errorMessage = new ErrorMessage(ex.getMessage(), 500, "http//google.com");

		return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorMessage).build();

	}

}
