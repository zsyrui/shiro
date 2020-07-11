package com.zsy.admin.domian.po;

public class Route {
    private Integer id;

    private String name;

    private String parentid;

    private String component;

    private String url;

    private String path;

    public Route(Integer id, String name, String parentid, String component, String url, String path) {
        this.id = id;
        this.name = name;
        this.parentid = parentid;
        this.component = component;
        this.url = url;
        this.path = path;
    }

    public Route() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid == null ? null : parentid.trim();
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component == null ? null : component.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path == null ? null : path.trim();
    }
}