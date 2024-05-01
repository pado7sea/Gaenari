//예시임 똑같이 쓰면 조짐

class User {
  int? id;
  String? nickname;
  String? email;

  User({this.id, this.nickname, this.email});

  factory User.fromJson(Map<String, dynamic> json) {
    return User(
      id: json['id'],
      nickname: json['nickname'],
      email: json['email'],
    );
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['id'] = this.id;
    data['nickname'] = this.nickname;
    data['email'] = this.email;
    return data;
  }
}
