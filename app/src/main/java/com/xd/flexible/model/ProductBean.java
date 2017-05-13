package com.xd.flexible.model;

import java.io.Serializable;

/**
 * Created by Flexible on 2017/4/6 0006.
 */

public class ProductBean implements Serializable {

    /**
     * pro_id : 1
     * class_id : 3
     * pro_name : 清扬
     * point_type : 0
     * point : 1
     * pro_img :
     * price : 18
     * original_price : 15.98
     * supplier_id : 1
     * product_type : 热门产品
     * product_stock : 111
     * product_detail : <img src="data:image/jpeg;base64,/9j/4QEaRXhpZgAASUkqAAgAAAAIABIBAwABAAAAAQAAABoBBQABAAAAbgAAABsBBQABAAAAdgAAACgBAwABAAAAAgAAADEBAgAcAAAAfgAAADIBAgAUAAAAmgAAABMCAwABAAAAAQAAAGmHBAABAAAArgAAAAAAAADAxi0AECcAAMDGLQAQJwAAQUNEIFN5c3RlbXMgRGlnaXRhbCBJbWFnaW5nADIwMDQ6MDc6MjIgMTE6MDg6NTUABQAAkAcABAAAADAyMjCQkgIABAAAADY1MgACoAQAAQAAAIYBAAADoAQAAQAAACcBAAAFoAQAAQAAAPAAAAAAAAAAAgABAAIABAAAAFI5OAACAAcABAAAADAxMDAAAAAAwAAGAP/AABEIAScBhgMBIgACEQEDEQH/2wCEAAcEBQYFBAcGBQYHBwcIChELCgkJChUPEAwRGRYaGhgWGBgcHygiHB0mHhgYIy8jJikqLS0tGyExNDErNCgsLSsBCwsLDw0PHhERHkArJCtAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQP/EAJAAAQACAgMBAAAAAAAAAAAAAAACBQMEAQYHCBAAAQMCAgcGBQMDBAIDAQAAAQACAwQRBRIGEyExQVJxBxQiM1GRMmFyocEIFYE0QmIWFyOxJNElQ4LCAQEBAQEBAAAAAAAAAAAAAAAAAQMCBBEBAQACAgIDAAICAwAAAAAAAAECERIhAzETMkEiUQRhBUKB/9oADAMBAAIRAxEAPwD6Jke4PIDio6x/MUl8x3VRWbpLWP5imsfzFRRNiWsfzFNY/mKiibEtY/mKax/MVFE2Jax/MU1j+YqKJsS1j+YprH8xUUTYlrH8xTWP5ioomxLWP5imsfzFRRNiWsfzFNY/mKiibEtY/mKax/MVFE2Jax/MU1j+YqKJsS1j+YprH8xUUTYlrH8xTWP5ioomxLWP5imsfzFRRNiWsfzFNY/mKiibEtY/mKax/MVFE2Jax/MU1j+YqKJsS1j+YprH8xUUTYlrH8xTWP5ioomxLWP5imsfzFRRNiWsfzFNY/mKiibEtY/mKax/MVFE2Jax/MU1j+YqKJsS1j+YprH8xUUTY18UkfqG+I/F+Cq7O7mK38T8hv1fgquVSr2XzHdVFSl8x3VRUUREQEREBERAREQEREBERAREQEREBERAREQEREBERAREQEREBERAREQEREBERAREQEREBERAREQEREGrifkN+r8FVyscT8hv1fgquRKvZfMd1UVKXzHdVFFEREBERAREQEREBERAREQEREBERAREQEREBERAREQEREBERAREQEREBERAREQEREBERAREQEREBERBq4n5Dfq/BVcrHE/Ib9X4KrkSr2XzHdVFSl8x3VRRRERAREQEREBERAREQEREBERAREQEREBERAREQEREBERAREQEREBERAREQEREBERAREQEREBERAREQauJ+Q36vwVXKxxPyG/V+Cq5Eq9l8x3VRUpfMd1UUUREQEREBERAREQEREBERBwSBvICAg7iD/Kw1fwt6laklQ6JwDWZtouBvKk7y06skx5LDO3mHumdvM33WmJNYwEjb80XNy1dOphubbmdvMPdc5m+o91pBYpy/O0McW7bggblccpb2mWGpuLIuaN5A/lcZ28w91pMzatofvAU2tLtwupcu+lmHXbazt5h7pnbzD3WoRY2I2hRde3h3qcl+ON7MPULjO3mHuq2DW5y5znFtzsPBZl1llJenOGFs7bmdvMPdM7eYe612NBG0/MAKLhlNr3S2ybXhN622s7eYe65uPULTWJj5tb8RAG8WTHKX2mWGvSwzt5h7pnbzD3WopNYSLgj3U5WuuEjZzN5h7rkOaeI2/NalrGxXDWf8oIcQL7QrMv7S4ddN1ERdMxERAREQEREBERAREQEREGrifkN+r8FVyscT8hv1fgquRKvZfMd1UVKXzHdVFFEREBERAREQEREBERAREQYKv4W9StZ0bXOBcNoW1V/C3qtcAnZa6yy9t8PqAcAgmhYcryb9FtYaP8Akff0Wni2P4DhmMRUGIYhDBWzM1jICTmLbkX2DYLghaePCe648md9RN1r+E7FwtF3aJojG2P/AOcpMsmbVlocQ8N3lpA2geoXJ7Q9FhUup/3VuublBjEEhcC74RbLx4eqvxJ8re2/NYnzyteGsYHAXuCtSHtI0UmrTRxYqH1LX5HRNp5S4O5SMuw7Ds+SjH2i6IzmV0eJtk1DXOkc2mlOrDfiJ8Oy3H0XWGHG7c558ppZEl9nWO70Sx5T7Ks/3N0S7k6s/dT3ZrmtdN3WbKC6xaL5N5uLeqS9pmicTYXS4nIwThzoi6jnGcA2JHg4HYVz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">
     * create_time : 2017-04-01 15:03:00
     * update_time : 2017-04-07 13:24:26
     */

    public int pro_id;
    public int class_id;
    public String pro_name;
    public String point_type;
    public int point;
    public String pro_img;
    public int price;
    public double original_price;
    public int supplier_id;
    public String product_type;
    public int product_stock;
    public String product_detail;
    public String create_time;
    public String update_time;
    public int num;
    public int total;
}
