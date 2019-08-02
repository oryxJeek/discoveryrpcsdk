# discoveryrpcsdk
只是一个非常非常简单的基于consul和jsonrpc的调用sdk。待完善。

调用时：
    引用sdk</br>
     
    @Autowired
    private ServerDiscoverService serverDiscoverService;
    
    String response = serverDiscoverService.call("Operating", "menu", "getMenuList", null, null);
