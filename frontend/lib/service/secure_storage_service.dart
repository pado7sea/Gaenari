import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:jwt_decode/jwt_decode.dart';

class SecureStorageService {
  final _storage = FlutterSecureStorage();

  Future<int> saveToken(String accessToken) async {
    //토큰 먼저 저장
    await _storage.write(key: 'accessToken', value: accessToken);
    int userId = await decodeToken(accessToken);
    return userId;
  }

  Future<String?> getToken() async {
    //토큰 읽어와서 사용
    return await _storage.read(key: 'accessToken');
  }

  Future<void> deleteToken() async {
    //삭제
    await _storage.delete(key: 'accessToken');
    await _storage.delete(key: 'cookie');
  }

  Future<int> decodeToken(String token) async {
    Map<String, dynamic> payload = Jwt.parseJwt(token);
    print(
        "payloadpayloadpayloadpayloadpayloadpayloadpayloadpayloadpayload시큐어스토리지서비스에있음");
    print(payload);

    int userId = int.parse(payload['sub']);
    print("userIduserIduserIduserIduserIduserId:$userId");
    //payload에서 추출하기
    // String userId = payload['userId'];
    return userId;
  }
}
