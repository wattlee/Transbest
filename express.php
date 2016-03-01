<?php
header('Content-type:text/html;charset=utf-8');
//���������appkey
$appkey = "0e075d561520f869209e100bdc1e7d83";
//************1.վ��վ��ѯ����Ʊ�ۣ�************
$url = "http://apis.juhe.cn/train/s2swithprice";
$params = array(
      "start" => "",//����վ
      "end" => "",//�յ�վ
      "key" => $appkey,//Ӧ��APPKEY(Ӧ����ϸҳ��ѯ)
      "dtype" => "",//�������ݵĸ�ʽ,xml��json��Ĭ��json
);
$paramstring = http_build_query($params);
$content = juhecurl($url,$paramstring);
$result = json_decode($content,true);
if($result){
    if($result['error_code']=='0'){
        print_r($result);
    }else{
        echo $result['error_code'].":".$result['reason'];
    }
}else{
    echo "����ʧ��";
}
//**************************************************
 
 
 
//************2.12306ʵʱ��Ʊ��ѯ************
/*$url = "http://apis.juhe.cn/train/yp";
$params = array(
      "key" => $appkey,//Ӧ��APPKEY(Ӧ����ϸҳ��ѯ)
      "dtype" => "",//�������ݵĸ�ʽ,xml��json��Ĭ��json
      "from" => "",//����վ,�磺�Ϻ�����
      "to" => "",// ����վ,�磺������
      "date" => "",//�������ڣ�Ĭ�Ͻ���
      "tt" => "",//�������ͣ�Ĭ��ȫ�����磺G(����)��D(����)��T(�ؿ�)��Z(ֱ��)��K(����)��Q(����)
);
$paramstring = http_build_query($params);
$content = juhecurl($url,$paramstring);
$result = json_decode($content,true);
if($result){
    if($result['error_code']=='0'){
        print_r($result);
    }else{
        echo $result['error_code'].":".$result['reason'];
    }
}else{
    echo "����ʧ��";
}
//**************************************************
 

//************3.�г�վ���б�************
$url = "http://apis.juhe.cn/train/station.list.php";
$params = array(
      "key" => $appkey,//Ӧ��APPKEY(Ӧ����ϸҳ��ѯ)
      "dtype" => "",//�������ݵĸ�ʽ,xml��json��Ĭ��json
);
$paramstring = http_build_query($params);
$content = juhecurl($url,$paramstring);
$result = json_decode($content,true);
if($result){
    if($result['error_code']=='0'){
        print_r($result);
    }else{
        echo $result['error_code'].":".$result['reason'];
    }
}else{
    echo "����ʧ��";
}*/
//**************************************************
 
 
 
 
 
/**
 * ����ӿڷ�������
 * @param  string $url [�����URL��ַ]
 * @param  string $params [����Ĳ���]
 * @param  int $ipost [�Ƿ����POST��ʽ]
 * @return  string
 */
function juhecurl($url,$params=false,$ispost=0){
    $httpInfo = array();
    $ch = curl_init();
 
    curl_setopt( $ch, CURLOPT_HTTP_VERSION , CURL_HTTP_VERSION_1_1 );
    curl_setopt( $ch, CURLOPT_USERAGENT , 'JuheData' );
    curl_setopt( $ch, CURLOPT_CONNECTTIMEOUT , 60 );
    curl_setopt( $ch, CURLOPT_TIMEOUT , 60);
    curl_setopt( $ch, CURLOPT_RETURNTRANSFER , true );
    curl_setopt($ch, CURLOPT_FOLLOWLOCATION, true);
    if( $ispost )
    {
        curl_setopt( $ch , CURLOPT_POST , true );
        curl_setopt( $ch , CURLOPT_POSTFIELDS , $params );
        curl_setopt( $ch , CURLOPT_URL , $url );
    }
    else
    {
        if($params){
            curl_setopt( $ch , CURLOPT_URL , $url.'?'.$params );
        }else{
            curl_setopt( $ch , CURLOPT_URL , $url);
        }
    }
    $response = curl_exec( $ch );
    if ($response === FALSE) {
        //echo "cURL Error: " . curl_error($ch);
        return false;
    }
    $httpCode = curl_getinfo( $ch , CURLINFO_HTTP_CODE );
    $httpInfo = array_merge( $httpInfo , curl_getinfo( $ch ) );
    curl_close( $ch );
    return $response;
}