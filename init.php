<?php 
require_once './common.php';
class Init extends Common
{
    public function index()
    {
        $this->check();
        //获取版本升级信息
        $versionUpgrade = $this -> getversionUpgrade($this->app['id']);
        if($versionUpgrade)
        {
            if($versionUpgrade['type'] && $this->params['version_id']<$versionUpgrade['type'])
            {//如果存在且比数据库版本低则进行升级
                $versionUpgrade['is_upload']=$versionUpgrade['type'];
            }
            else{$versionUpgrade['is_upload']=0;}
            return Response::show(200,'获取版本信息成功',$versionUpgrade);
        }
        else{
            return Response::show(400,'版本升级信息获取失败',$versionUpgrade);
        }
    }
}
$init=new Init;
$init->index();
