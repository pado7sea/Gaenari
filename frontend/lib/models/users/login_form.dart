class LoginForm {
  String? accountId;
  String? password;

  LoginForm({this.accountId, this.password});

  LoginForm.fromJson(Map<String, dynamic> json) {
    accountId = json['accountId'];
    password = json['password'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = <String, dynamic>{};
    data['accountId'] = accountId;
    data['password'] = password;
    return data;
  }
}
