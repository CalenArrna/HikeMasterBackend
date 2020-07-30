package org.backend.DTOs;

import org.backend.Model.Pictures;

public class ImageSuccessDTO extends ResponseDTO{
    private Pictures pictures;

    public ImageSuccessDTO(Pictures pictures) {
       success=true;
      this.pictures=pictures;

    }
}
