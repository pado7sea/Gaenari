import 'package:flutter/material.dart';

class NonePageRoute extends PageRouteBuilder {
  final Widget nextPage;

  NonePageRoute({required this.nextPage})
      : super(
          pageBuilder: (context, animation, secondaryAnimation) => nextPage,
          transitionsBuilder: (context, animation, secondaryAnimation, child) {
            // 이전 페이지와 다음 페이지 사이의 전환 애니메이션 로직
            // 여기에 애니메이션을 정의할 수 있습니다.
            // 이 경우에는 애니메이션 없이 바로 다음 페이지를 보여줍니다.
            return child;
          },
        );
}


//아래처름 쓰면 됨미다

// Navigator.push(
//   context,
//   NonePageRoute(nextPage: ChallengePage()),
// );
